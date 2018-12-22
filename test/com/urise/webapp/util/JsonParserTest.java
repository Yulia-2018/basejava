package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JsonParserTest {

    private static final List<Resume> LIST_RESUME = ResumeTestData.createResume();
    private static final Resume RESUME_1 = LIST_RESUME.get(0);
    private static final Resume RESUME_2 = LIST_RESUME.get(1);
    private static final Resume RESUME_3 = LIST_RESUME.get(2);
    private static final Resume RESUME_4 = LIST_RESUME.get(3);
    // For method "update"
    private static final Resume RESUME_5 = LIST_RESUME.get(4);

    private static final String UUID_1 = RESUME_1.getUuid();
    private static final String UUID_2 = RESUME_2.getUuid();
    private static final String UUID_3 = RESUME_3.getUuid();
    private static final String UUID_4 = RESUME_4.getUuid();

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}