//package org.springframework.safe.app;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.common.properties.GzrobotProperties;
//import org.springframework.core.annotation.Order;
//import org.springframework.safe.utils.LicenseUtil;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
///**
// * @author Sir.D
// */
//@Component
//@Order(Integer.MIN_VALUE)
//public class LicenseRunner  {
//    @Autowired
//    private GzrobotProperties gzrobotProperties;
//
//    @PostConstruct
//    public void onApplicationEvent() {
//        if (!LicenseUtil.authLicense(gzrobotProperties.getLicense().getIsDocker())){
//            System.exit(0);
//        }
//    }
//}
