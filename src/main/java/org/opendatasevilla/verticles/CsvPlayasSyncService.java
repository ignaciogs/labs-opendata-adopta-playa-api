package org.opendatasevilla.verticles;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@Service
public class CsvPlayasSyncService {

    @Value("https://docs.google.com/spreadsheet/ccc?key=0AjFc2buJLAR5dDQ4ZE80dUszWEhRbjU2NzNRYV92NkE&output=csv")
    private String csvUrl;

    private static final Logger logger = LoggerFactory.getLogger(CsvPlayasSyncService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Scheduled(fixedDelay = 1000 * 3600) //once every hour
    public void doSync() {

        //Comunidad|0	Provincia|1	Municipio|2	Nombre|3	punto_muestreo|4	adoptada_por|5	utm_x|6	utm_y|7
        // utm_huso|8	fecha_toma|9	escherichia_coli|10	enterococo|11	observaciones|12

        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(csvUrl);
            HttpResponse response = client.execute(request);

// Get the response
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            CSVReader reader = new CSVReader(in);
            reader.readNext(); //skip first line with headers
            String[] line;
            int imported = 0;
            while ((line = reader.readNext()) != null) {
                try {
                    Playa playa = new Playa();
                    playa.setComunidad(line[0]);
                    playa.setProvincia(line[1]);
                    playa.setMunicipio(line[2]);
                    playa.setNombre(line[3]);
                    playa.setPuntoMuestreo(StringUtils.hasText(line[4]) ? Integer.valueOf(line[4].trim()) : null);
                    playa.setAdoptadaPor(line[5]);
                    playa.setUtmX(StringUtils.hasText(line[6]) ? Double.valueOf(line[6].trim()) : null);
                    playa.setUtmY(StringUtils.hasText(line[7]) ? Double.valueOf(line[7].trim()) : null);
                    playa.setUtmHuso(StringUtils.hasText(line[8]) ? Integer.valueOf(getFirstNumber(line[8])) : null);
                    playa.setFechaToma(StringUtils.hasText(line[9]) ? new SimpleDateFormat("dd/MM/yyyy").parse(line[9].trim()) : null);
                    playa.setEscherichiaColi(StringUtils.hasText(line[10]) ? Integer.valueOf(getFirstNumber(line[10])) : null);
                    playa.setEnterococo(StringUtils.hasText(line[11]) ? Integer.valueOf(getFirstNumber(line[11])) : null);
                    playa.setObservaciones(line[12]);
                    String id = String.format("%s_%s", playa.getNombre().replaceAll(" ", "_"), playa.getMunicipio().replaceAll(" ", "_"));
                    playa.setId(id);
                    applicationContext.getBean(getClass()).save(playa, id);
                    imported++;
                    logger.debug(String.format("imported %d : %s", imported, playa.getNombre()));

                } catch (Throwable t) {
                    logger.error("error importing playa", t.getMessage());
                }
            }
            reader.close();
            in.close();
        } catch (Exception ex) {
            logger.error("error importing playa", ex.getMessage());
        }
    }

    private static String getFirstNumber(String text) {
        String number = null;
        if (StringUtils.hasText(text)) {
            number = text.trim().split(" ")[0];
        }
        return number;
    }

    @Transactional
    public void save(Object instance, Object id) {
        if (entityManager.find(Playa.class, id) != null) {
            instance = entityManager.merge(instance);
        } else {
            entityManager.persist(instance);
        }
    }

}
