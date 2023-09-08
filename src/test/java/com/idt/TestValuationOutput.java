package com.idt;

import com.idt.Pages.ValueMyCarPage;
import com.idt.Pages.ValueEstimatePage;
import com.idt.util.ReadFile;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;


public class TestValuationOutput {

    final String CAR_INPUT_V2 = "car_input_v2.txt";
    final String CAR_INPUT_V3 = "car_input_v3.txt";
    final String CAR_OUTPUT_V2 = "car_output_v2.txt";
    final String FILE_PATH = "src/main/resources/";
    final String REG_NO_NOT_IN_OUTPUT = "Reg No does not exist in output file";
    final String REG_NO_NOT_FOUND_TEXT = "We couldn't find a car with that registration. Check your registration number is correct and try again.";
    final String[] IN_CORRECT_REG_NOS = {"BW57BOW", "HL17DLX", "NK18HTN"};

    ArrayList<String> fileInputs = new ArrayList<>();
    ArrayList<String> fileOutputs = new ArrayList<>();

    @Test
    public void testVehicleDetailsSearch() {
        fileInputs.add(CAR_INPUT_V2);
        //fileInputs.add(CAR_INPUT_V3);
        fileOutputs.add(CAR_OUTPUT_V2);
        ValueMyCarPage vmc = new ValueMyCarPage();
        ReadFile read = new ReadFile();

        String carOutputFromFile = read.readFile(FILE_PATH, fileOutputs);
        Map<String, String> carOutputFromFileMap = read.storeOutputAsMap(carOutputFromFile);
        Map<String, String> carInputFromFile = read.readFileMap(FILE_PATH, fileInputs);
        assertSoftly(softly -> {
            fileInputs.forEach(fileInput -> {
                System.out.println("=============== Running vehicle details search using Registration Numbers from file: " + fileInput + " ====================");
                List<String> carInputFromFileList = read.storeInputAsList(fileInput, carInputFromFile);
                carInputFromFileList.stream().forEach(regNo -> {
                    if (Arrays.asList(IN_CORRECT_REG_NOS).contains(regNo)) {
                        String actualText = vmc.regNotFound(regNo);
                        softly.assertThat(REG_NO_NOT_FOUND_TEXT).isEqualToIgnoringCase(actualText);
                        vmc.goBack();
                    } else {
                        ValueEstimatePage ve = vmc.getValuation(regNo);
                        String value = carOutputFromFileMap.getOrDefault(regNo, REG_NO_NOT_IN_OUTPUT);
                        softly.assertThat(value.split(",")[0]).withFailMessage("Registration No %s not matching", regNo)
                                .isEqualToIgnoringCase(ve.getRegistration());
                        softly.assertThat(value.split(",")[1]).withFailMessage("Make for %s not matching", regNo)
                                .isEqualToIgnoringCase(ve.getMake());
                        softly.assertThat(value.split(",")[2]).withFailMessage("Model for %s not matching", regNo).
                                isEqualToIgnoringCase(ve.getModel());
                        ve.returnToValuationSearchPage();
                    }
                });
            });
        });
    }

    @AfterAll
    static void tearDown() {
        ValueMyCarPage.closeBrowser();
    }
}
