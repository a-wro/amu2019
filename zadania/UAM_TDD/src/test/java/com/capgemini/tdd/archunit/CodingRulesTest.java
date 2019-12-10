package com.capgemini.tdd.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

public class CodingRulesTest
{

    @ArchTest
    public static final ArchRule fieldsShouldBePrivate =
            fields().should().bePrivate().because("we agreed on this convention");

    @ArchTest
    public static final ArchRule serviceName = classes().should().haveSimpleNameEndingWith("Impl");

    @Test
    public void ruleCheckForFields()
    {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.capgemini.tdd.services.game.impl");
        fieldsShouldBePrivate.check(importedClasses);
    }

    @Test
    public void checkServiceImplName()
    {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.capgemini.tdd.core.service.impl");
        serviceName.check(importedClasses);
    }

}
