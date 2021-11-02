package helpers.listeners;

import helpers.contexts.TestContext;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.TestResult;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.qameta.allure.util.ResultsUtils.bytesToHex;
import static io.qameta.allure.util.ResultsUtils.getMd5Digest;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;

public class CucumberTestLyfecycleListener implements TestLifecycleListener {
    @Override
    public void beforeTestStart(TestResult result) {
        Map<String, String> testParameters = TestContext.context().getCurrentXmlTest().getAllParameters();
        List<Parameter> parameters = testParameters.entrySet().stream()
                .map(entry -> new Parameter().setName(entry.getKey()).setValue(entry.getValue()))
                .collect(Collectors.toList());
        String historyId = getHistoryId(result.getFullName(), parameters);
        result.setHistoryId(historyId);
        result.setParameters(parameters);
    }

    protected String getHistoryId(String scenarioFullName, List<Parameter> parameters) {
        final MessageDigest digest = getMd5Digest();
        digest.update(scenarioFullName.getBytes(UTF_8));
        parameters.stream()
                .sorted(comparing(Parameter::getName).thenComparing(Parameter::getValue))
                .forEachOrdered(parameter -> {
                    digest.update(parameter.getName().getBytes(UTF_8));
                    digest.update(parameter.getValue().getBytes(UTF_8));
                });
        final byte[] bytes = digest.digest();
        return bytesToHex(bytes);
    }
}
