import { Job } from "./job.model";

export class JobRequest {
     name: string;
     requestUrl: string;
     requestParams: string[];
     type: string;
     executionType: string;
     priority: string;
     status: string;
     scheduleInterval: string;


     constructor(job: Job) {
          this.name = job.name;
          this.requestUrl = job.requestUrl
          this.requestParams = job.requestParamsCommaDelimited.split(",");
          this.type = job.type;
          this.executionType = job.executionType;
          this.priority = job.priority;
          this.status = "NEW";
          this.scheduleInterval = job.scheduleInterval;
     }
}