package com.jingdianjichi.demo.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * @description:
 * @fileName: ArchUnitTest
 * @author: zerowindy
 * @createAt: 2023/6/29 10:54
 * @updateBy: zerowindy 2023/6/29 10:54
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.jingdianjichi")
public class ArchUnitTest {

    @ArchTest
    //@ArchIgnore
    public static final ArchRule toolsShouldEndWithUtils = classes()
            .that().resideInAPackage("com.jingdianjichi.tool")
            .should().haveSimpleNameEndingWith("Utils");

    @ArchTest
    public static final ArchRule controllerShouldAnnotationedRestController = classes()
            .that().resideInAPackage("com.jingdianjichi..controller")
            .should().haveSimpleNameEndingWith("Controller");

}
