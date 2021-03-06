package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @Override
    @BeforeEach
    void init() {
        manager = new InMemoryTaskManager();
        super.init();
    }
    @Test
    void inMemoryTaskManagerTest(){
        manager=new InMemoryTaskManager();
        assertEquals(0,manager.getTasksList().size(),"Task List is empty");
        assertEquals(0,manager.getSubtasksList().size(),"Subtask List is empty");
        assertEquals(0,manager.getEpicsList().size(),"EPIC List is empty");
        assertEquals(0,manager.getHistory().size(),"History is empty");
    }
}