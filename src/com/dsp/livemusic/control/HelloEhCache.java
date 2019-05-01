package com.dsp.livemusic;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Cache;

/**
 * Created by Natnael Zeleke on 2/7/2016.
 */
public class HelloEhCache {


    public static void main(String[] args){


        CacheManager cm = CacheManager.getInstance();

        cm.addCache("Music_Player");

        Cache cache = cm.getCache("cache1");

        cache.put(new Element("1","Jan"));

        cache.put(new Element("2","Feb"));

        Element ele = cache.get("1");

        System.out.println(ele.getObjectValue().toString());

        System.out.println(cache.isKeyInCache("1"));


        cm.shutdown();





    }
}
