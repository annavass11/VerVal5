package service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class ServiceMockTest {

    Service service;
    @Mock
    private StudentXMLRepository studentXMLRepository;
    @Mock
    private HomeworkXMLRepository homeworkXMLRepository;
    @Mock
    private GradeXMLRepository gradeXMLRepository;

    @BeforeEach
    public void init() {
        studentXMLRepository = mock(StudentXMLRepository.class);
        homeworkXMLRepository = mock(HomeworkXMLRepository.class);
        gradeXMLRepository = mock(GradeXMLRepository.class);

        Map<Integer, Homework> homeworks = new HashMap<>();
        homeworks.put(1,new Homework("1", "description", 5,2));
        Iterable<Homework> homeworkList = homeworks.values();

        Mockito.when(homeworkXMLRepository.save(any(Homework.class))).thenReturn(new Homework("a", "b", 12, 13));
        Mockito.when(homeworkXMLRepository.findAll()).thenReturn(homeworkList);
        Mockito.when(studentXMLRepository.save(any(Student.class))).thenReturn(new Student("id", "name", 500));
        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    public void testSaveHomework() {
        assertEquals(1, service.saveHomework("1", "Hard", 5, 1));
        Mockito.verify(homeworkXMLRepository).save(any(Homework.class));
    }

    @Test
    public void testDeleteHomework() {
        assertNotNull(service.findAllHomework());
    }

    @Test
    void testValidGroupNumber() {
        assertTrue(service.saveStudent("1", "Acbhj", 287) == 1);
        Mockito.verify(studentXMLRepository).save(any(Student.class));
    }
}