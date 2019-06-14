package com.kgpco.vz.allure;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import io.qameta.allure.Aggregator;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.entity.Status;
import io.qameta.allure.entity.TestResult;

public class Plugin implements Aggregator{
		  @Override
		    public void aggregate(final Configuration configuration,
		                          final List<LaunchResults> launches,
		                          final Path outputDirectory) throws IOException {
		    final JacksonContext jacksonContext = configuration
		        .requireContext(JacksonContext.class);
		    final Path dataFolder = Files.createDirectories(outputDirectory.resolve("data"));
		    System.out.println(dataFolder.toFile().getAbsolutePath());
		    final Path dataFile = dataFolder.resolve("plugindata.json");
		    final Stream<TestResult> resultsStream = launches.stream()
		        .flatMap(launch -> launch.getAllResults().stream());
		    try (OutputStream os = Files.newOutputStream(dataFile)) {
		        jacksonContext.getValue().writeValue(os, extractData(resultsStream));
		    	}
  }
	  
	  private Collection<Map> extractData(final Stream<TestResult> testResults) {
		  Collection<Map> data = new HashSet<Map>();
		  Map<String, Object> map = new HashMap<>();
		  map.put("sounds", new String[] {"Growl!", "Hiss!"});
		  map.put("name", "angryCat");
		  data.add(map);
		  map = new HashMap<>();
		  map.put("sounds", new String[] {"Oink!", "Meow!"});
		  map.put("name", "hungryCat");
		  data.add(map);
		  map = new HashMap<>();
		  map.put("sounds", new String[] {"Bark!", "Woof!", "Moo!"});
		  map.put("name", "angryCat");
		  data.add(map);
		  return data;
		  
	    }
	  
	  public Object getData(Configuration configuration, List<LaunchResults> launches) {
	        Stream<TestResult> filteredResults = launches.stream().flatMap(launch -> launch.getAllResults().stream())
	                .filter(result -> result.getStatus().equals(Status.FAILED));
	        return extractData(filteredResults);
	    }

    

}
