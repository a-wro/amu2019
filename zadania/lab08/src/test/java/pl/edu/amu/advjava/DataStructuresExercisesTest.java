package pl.edu.amu.advjava;


import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class DataStructuresExercisesTest {

    @Test
    public void testExercises() {
        DataStructuresExercises dse = new DataStructuresExercises();

        dse.indexWords(asList("foo","bar","baz"), "file1");
        dse.indexWords(asList("foo","bar"), "file2");
        dse.indexWords(asList("bar","baz"), "file3");

        dse.indexAll(otherIndex());

        assertThat(dse.getMatchingFiles("foo")).containsExactlyInAnyOrder("file1","file2", "file4");
        assertThat(dse.getMatchingFiles("bar")).containsExactlyInAnyOrder("file1","file2", "file3");
        assertThat(dse.getMatchingFiles("baz")).containsExactlyInAnyOrder("file1","file3");
        assertThat(dse.getMatchingFiles("foobar")).containsExactlyInAnyOrder("file5");

        dse.fileDeleted("file5");
        assertThat(dse.getMatchingFiles("foobar")).isEmpty();

        dse.fileDeleted("file2");
        assertThat(dse.getMatchingFiles("foo")).containsExactlyInAnyOrder("file1", "file4");
        assertThat(dse.getMatchingFiles("bar")).containsExactlyInAnyOrder("file1", "file3");
    }

    private Map<String, Set<String>> otherIndex() {
        Map<String, Set<String>> otherIndex = new HashMap<>();
        otherIndex.put("foo", new HashSet<>(asList("file4")));
        otherIndex.put("foobar", new HashSet<>(asList("file5")));
        return otherIndex;
    }

}