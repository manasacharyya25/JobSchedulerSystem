import { HttpClient } from '@angular/common/http';
import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { AppConfiguration } from './app.configuration';
import { Job } from './job.model';
import { JobRequest } from './jobRequest.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'JobSchedulerSystem';
  isAddNewJobActive: boolean;
  job: Job;
  jobList: any;


  constructor(private httpClient: HttpClient) {
    this.isAddNewJobActive = false;
    this.job = new Job();
    this.jobList = []    
  }

  ngOnInit(): void {
      // this.getJobList();

      var j1 = new Job();
      j1.id = "1";
      j1.name = "Test Job 1";

      var j2 = new Job();
      j2.id = "2";
      j2.name = "Test Job 2";
      j2.status = "QUEUED";

      var j3 = new Job();
      j3.id = "3";
      j3.name = "Test Job 3";
      j3.status = "RUNNING";

      var j4 = new Job();
      j4.id = "4";
      j4.name = "Test Job 4";
      j4.status = "SUCCESS";

      var j5 = new Job();
      j5.id = "5";
      j5.name = "Test Job 5";
      j5.status = "FAILURE";

      // this.jobList = [j1, j2, j3, j4, j5];

      this.getJobList();
  }

  toggleAddNewJob(): void {
    this.isAddNewJobActive = !this.isAddNewJobActive;
  }

  postJob(): void {
    var jobRequest = new JobRequest(this.job);

    this.httpClient.post(
            `${AppConfiguration.BACKEND_ENDPOINT}/scheduler`,
            jobRequest
        ).subscribe();

    setTimeout(() => {
      this.isAddNewJobActive = false;
      this.getJobList();
    }, 2000);
  }

   async getJobList() {
    await this.httpClient.get(`${AppConfiguration.BACKEND_ENDPOINT}/scheduler`)
            .toPromise().then(response => {
              this.jobList = response;
              console.log(this.jobList);
            }).catch(error => console.error(error));
  }

  getJobClass(job: Job): string {
    switch (job.status) {
      case "NEW":
        return "active";
        break;
      case "QUEUED":
        return "info";
        break;
      case "RUNNING":
        return "warning";
        break;
      case "SUCCESS":
        return "success"
        break;
      case "FAILURE":
        return "danger";
        break;
      default:
        return ""
    }
  }

  getPriorityBadge(job: Job): string {
    switch(job.priority) {
      case "HIGH":
        return "badge badge-high"
      case "LOW":
        return "badge"
      default:
        return ""
    } 
  }
}

 

