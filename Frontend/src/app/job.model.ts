export class Job {
     id: string;
     name: string;
     requestUrl: string;
     requestParamsCommaDelimited: string;
     requestParams: string[];
     type: string;
     executionType: string;
     priority: string;
     status: string;
     scheduleInterval: string;


     constructor() {
          this.id = "";
          this.name = "";
          this.requestUrl = "";
          this.requestParamsCommaDelimited = "";
          this.requestParams = [];
          this.type = "";
          this.executionType = "";
          this.priority = "";
          this.status = "NEW";
          this.scheduleInterval = "";

     }
}