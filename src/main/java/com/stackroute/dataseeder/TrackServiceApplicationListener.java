package com.stackroute.dataseeder;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackAlreadyExistsException;
import com.stackroute.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
//used to specify the path of the resource file.
class ApplicationListerSeedata implements ApplicationListener<ContextRefreshedEvent> {

    @Qualifier("trackService")
    //Used to particularly mention the bean name.
    private TrackService trackService;

    @Autowired
    public ApplicationListerSeedata(TrackService trackService) {
        this.trackService = trackService;
    }

    @Value("${track1.id}")
    private int trackId;
    @Value("${track1.name}")
    private String trackName;
    @Value("${track1.comments}")
    private String trackComments;

    @Autowired
    private Environment environment;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        Track track1 = new Track(trackId, trackName, trackComments);
        Track track2 = new Track(Integer.parseInt(environment.getProperty("track2.id")),
                environment.getProperty("track2.name"), environment.getProperty("track2.comments"));
        try
        {
            trackService.saveTrack(track1);
            trackService.saveTrack(track2);
        }catch (TrackAlreadyExistsException ex){
            ex.printStackTrace();
        }
    }
}