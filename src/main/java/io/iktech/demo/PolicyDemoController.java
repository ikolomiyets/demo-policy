package io.iktech.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@RestController
public class PolicyDemoController {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static Policy[] policies;

    @RequestMapping(
            value = "/policies",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<Policies> getPolicies(@RequestParam(value = "first", defaultValue = "1") int first, @RequestParam(value = "count", defaultValue = "10") int count) {
        Policies policies = new Policies();
        policies.setFirst(first);
        policies.setNext(-1);
        policies.setPolicies(Arrays.asList(Arrays.copyOfRange(PolicyDemoController.policies, first - 1, PolicyDemoController.policies.length)));
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(200);
        return builder.body(policies);
    }

    static {
        try {
            policies = new Policy[] {
                    new Policy("IE-8A-219276", format.parse("2017-05-01"), new Long[] { 2L }),
                    new Policy("IE-8A-210117", format.parse("2017-04-17"), new Long[] { 1L, 4L }),
                    new Policy("IE-8C-001729", format.parse("2017-10-11"), new Long[] { 16L })
            };

        } catch(Exception e) {

        }
    }
}
