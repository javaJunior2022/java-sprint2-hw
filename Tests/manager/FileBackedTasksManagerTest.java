package manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @Override
    @BeforeEach
   void init() {
        manager = new FileBackedTasksManager(new File("tasks.csv"), true);
        super.init();
    }
    @Test
    void fileBackedTasksManagerTest(){
        manager= new FileBackedTasksManager(new File("tasks.csv"), false);
        assertEquals(0,manager.getTasksList().size(),"Task List is empty");
        assertEquals(0,manager.getSubtasksList().size(),"Subtask List is empty");
        assertEquals(0,manager.getEpicsList().size(),"EPIC List is empty");
        assertEquals(0,manager.getHistory().size(),"History is empty");
    }
}