package com.douzone.bit.pathfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfig {

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer conf = new TilesConfigurer();

		conf.setDefinitions(new String[] { "/WEB-INF/tiles/tiles.xml" });
		conf.setCheckRefresh(true);

		return conf;
	}

	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tv = new UrlBasedViewResolver();
		tv.setViewClass(TilesView.class);
		tv.setOrder(1);

		return tv;
	}
}