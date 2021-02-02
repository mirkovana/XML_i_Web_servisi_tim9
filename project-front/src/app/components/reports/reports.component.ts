import { Component, OnInit } from '@angular/core';
import { ReportsService } from '../../service/reports.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ReportItem } from '../../model/report.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reports: Array<ReportItem> = [];

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });
  
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

  submit1() {
    console.log("form = ", this.myForm1.value);
    if (this.myForm1.value.keywords == "") {
      this.getReports();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <keywordSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <keywords>`+ this.myForm1.value.keywords + `</keywords>
    </keywordSearch>`;

    this.service.searchByKeywords(xml).subscribe((data: any) => {
      console.log("data = ", data);
      this.reports = data;
    }, error => {
      console.log(error);
    });
  }
}
