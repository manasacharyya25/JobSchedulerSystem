package com.example.SampleJobApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("job")
public class JobController
{
  @Value("${response.delay}")
  private int responseDelay;

  private String response;

  @GetMapping("short-success/{message}")
  public ResponseEntity<String> shortLivedSuccess(@PathVariable String message) throws InterruptedException
  {
    System.out.println("Processing Message for Success: "+ message);
    TimeUnit.SECONDS.sleep(responseDelay);
    System.out.println("Processing Complete for Success: "+ message);
    return ResponseEntity.ok("Message : '"+message + "' Processing Complete");
  }

  @GetMapping("short-failure/{message}")
  public ResponseEntity<String> shortLivedFailure(@PathVariable String message) throws InterruptedException
  {
    System.out.println("Processing Message for Failure: "+ message);
    TimeUnit.SECONDS.sleep(responseDelay);
    System.out.println("Processing Complete for Failure: "+ message);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message : '"+message + "' Processing Failed");
  }

  @GetMapping("batch/{message}")
  public void batchJob(@PathVariable String message) throws InterruptedException {
   TimeUnit.MINUTES.sleep(3);
  }

}
