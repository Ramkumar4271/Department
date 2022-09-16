package com.ram.api.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Endpoint(id = "features")
public class FeatureEndpoint {
	
	private final Map<String, Feature> featureMap = new ConcurrentHashMap<>();
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Feature{
		private boolean isEnabled;
	}
	
	public FeatureEndpoint() {
		this.featureMap.put("Department", new Feature(true));
		this.featureMap.put("User", new Feature(false));
		this.featureMap.put("Authentication", new Feature(false));
	}
	
	@ReadOperation
	public Map<String, Feature> features() {
		return this.featureMap;
	}
	
	@ReadOperation
	public Feature feature(@Selector String featureName) {
		return this.featureMap.get(featureName);
	}
}
