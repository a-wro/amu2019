package pl.edu.amu.advjava;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
    ZADANIE: zmodyfikuj kod, stosując programowanie funkcyjne tam, gdzie to możliwe
     */
final class DataStructuresExercises {

    Map<String, Set<String>> wordsIndex = new HashMap<>();

    void indexWords(List<String> words, String fileName) {
        if(words == null || fileName == null) {
            throw new IllegalArgumentException();
        }

        words.forEach(word -> {
            wordsIndex.putIfAbsent(word, new HashSet<>());
            wordsIndex.get(word).add(fileName);
        });
//        for(String word : words) {
//            if(!wordsIndex.containsKey(word)){
//                wordsIndex.put(word, new HashSet<>());
//            }
//            wordsIndex.get(word).add(fileName);
//        }
    }

    Set<String> getMatchingFiles(String word) {
        return wordsIndex.getOrDefault(word, new HashSet<>());
//        if(word != null && wordsIndex.containsKey(word)) {
//            return new HashSet<>(wordsIndex.get(word));
//        }
//        return new HashSet<>();
    }

    void indexAll(Map<String, Set<String>> otherIndex) {

        otherIndex.entrySet().forEach(entry -> {
            String word = entry.getKey();
            wordsIndex.putIfAbsent(word, new HashSet<>());
            wordsIndex.get(word).addAll(entry.getValue());
        });

//        for(Map.Entry<String, Set<String>> entry : otherIndex.entrySet()) {
//            String word = entry.getKey();
//            if(!wordsIndex.containsKey(word)){
//                wordsIndex.put(word, new HashSet<>());
//            }
//            wordsIndex.get(word).addAll(entry.getValue());
//        }
    }

    void fileDeleted(String fileName) {

        wordsIndex.values().forEach(set -> set.remove(fileName));
        wordsIndex.keySet().stream().filter(key -> !wordsIndex.get(key).isEmpty());

//        Set<Map.Entry<String, Set<String>>> mapEntries = wordsIndex.entrySet();
//        Iterator<Map.Entry<String, Set<String>>> entriesIterator = mapEntries.iterator();
//        while(entriesIterator.hasNext()) {
//            Map.Entry<String, Set<String>> entry = entriesIterator.next();
//
//            entry.getValue().remove(fileName);
//            if(entry.getValue().isEmpty()){
//                entriesIterator.remove();
//            }
//        }

    }

}