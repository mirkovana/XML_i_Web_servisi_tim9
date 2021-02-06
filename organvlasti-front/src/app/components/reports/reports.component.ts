import { Component, OnInit } from '@angular/core';
import { ReportsService } from '../../service/reports.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ReportItem } from '../../model/report.model';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reports: Array<ReportItem> = [];

  constructor(private service: ReportsService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    console.log("get reports");
    this.getReports();
  }

  getReports(){
    this.service.getReports().subscribe((data: ReportItem[])  => {
      console.log(data);
      this.reports = data;
    }, error => {
      console.log(error);
    });
  }

  generateReport(){
    console.log("generate report");
    this.service.generateReport(localStorage.getItem('username')).subscribe((data: any[])  => {
      console.log("success = ", data);
      this.getReports();
    }, error => {
      console.log("error = ", error);
    });
  }

  convertTimestamp(time: string): string {
    let date = new Date(parseInt(time));
    return date.toLocaleString();
  }
}
