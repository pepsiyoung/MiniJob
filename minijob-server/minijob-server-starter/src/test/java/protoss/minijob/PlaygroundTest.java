package protoss.minijob;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PlaygroundTest.class)
public class PlaygroundTest {

    @Test
    public void caseOne() {

        List<Long> appIds = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            appIds.add((long) i);
        }

        Lists.partition(appIds, 10).forEach(System.out::println);
    }
}
