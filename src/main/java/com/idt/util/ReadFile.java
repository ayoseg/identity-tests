package com.idt.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReadFile {

    public Map<String, String> readFileMap (String filePath, List<String> fileNames){
        Map <String, String> fileContents = fileNames.stream().map(fileName -> {
                    String _fileName = filePath + fileName;
            String fileContent;
                    try {
                        fileContent = Files.readAllLines(Paths.get(_fileName)).toString();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            return Map.entry(fileName, fileContent);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return fileContents;
    }

    public String readFile(String filePath, List<String>fileNames) {
        String outputValue = readFileMap(filePath,  fileNames).get(fileNames.get(0));
        System.out.println(outputValue);
        return outputValue;
    }

    public Map<String, String> storeOutputAsMap(String carOutput){

        Map <String, String> outputAsMap = new HashMap<>();

        String outputlineRegex = "(?<=(,\\s){3})(.*?)(?=(,\\s){3}|])";
        String regNoMatchRegex = "\\b[a-z]{2}([1-9]{2})[a-z]{3}\\b";

        Pattern pattern = Pattern.compile(outputlineRegex, Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile(regNoMatchRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(carOutput);

        while (matcher.find()) {
            Matcher matcher1 = pattern1.matcher(matcher.group());
            while (matcher1.find()) {
                outputAsMap.put(matcher1.group(), matcher.group().toString().replace(", ", ""));
            }
        }
        System.out.println(outputAsMap);
        return outputAsMap;
    }

    public List<String> storeInputAsList(String fileName, Map <String, String> inputContentMap){
        ArrayList<String> regNoList = new ArrayList<>();
        String matchPlatesRegex = "\\b[a-z]{2}([1-9]{2})\\s{0,1}[a-z]{3}\\b";

        Pattern pattern = Pattern.compile(matchPlatesRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputContentMap.get(fileName));
        while (matcher.find()) {
            regNoList.add(matcher.group().replace(" ", ""));
        }
        System.out.println(regNoList);
        return regNoList;
    }
}