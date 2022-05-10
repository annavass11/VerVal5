package service;

//import junit.framework.TestCase;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    /*Validator<Student> studentValidator = new StudentValidator();
    Validator<Homework> homeworkValidator = new HomeworkValidator();
    Validator<Grade> gradeValidator = new GradeValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
    HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
    GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);*/

    static Service service;

    @BeforeAll
    public static void init() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);

        System.out.println("init");
    }

    public <T> int getIterableLength(Iterable<T> list) {
        int counter = 0;
        for (Object i : list) {
            counter++;
        }
        return counter;
    }

    @Test
    public void testSaveStudentWithValidData() {
        Iterable<Student> students = service.findAllStudents();
        int count1 = getIterableLength(students);
        service.saveStudent("1", "Hello", 523);
        students = service.findAllStudents();
        int count2 = getIterableLength(students);
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void testSaveStudentWithInvalidData() {

        Throwable e = assertThrows(ValidationException.class, () -> {
            service.saveStudent("", "A", 500); //Wrong id
        });
        assertEquals("ID invalid! \n", e.getMessage());
        //System.out.println(e.getMessage() +"uj");

        Throwable e1 = assertThrows(ValidationException.class, () -> {
            service.saveStudent("1", "A", 1000); //Wrong group number
        });
        assertEquals("Group invalid! \n", e1.getMessage());
        //System.out.println(e.getMessage());

        Throwable e2 = assertThrows(ValidationException.class, () -> {
            service.saveStudent("1", "", 1000); //Wrong name
        });
        assertEquals("Name invalid! \n", e2.getMessage());

    }


    @Test
    public void testSaveHomework() {
        Iterable<Homework> homeworks = service.findAllHomework();
        int count1 = getIterableLength(homeworks);
        service.saveHomework("1", "Hard", 5, 1);
        homeworks = service.findAllHomework();
        int count2 = getIterableLength(homeworks);
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void testDeleteStudent() {
        Iterable<Student> students = service.findAllStudents();
        int count1 = getIterableLength(students);
        service.deleteStudent("1");
        students = service.findAllStudents();
        int count2 = getIterableLength(students);
        assertEquals(count1 - 1, count2);
    }

    @Test
    public void testDeleteHomework() {
        Iterable<Homework> homeworks = service.findAllHomework();
        int count1 = getIterableLength(homeworks);
        service.deleteHomework("1");
        homeworks = service.findAllHomework();
        int count2 = getIterableLength(homeworks);
        assertEquals(count1 - 1, count2);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 109, 980, 937, Integer.MAX_VALUE})
    void testInvalidGroupNumber(int number) {
        assertTrue(service.saveStudent("1", "Acbhj", number) == 1);
    }
}