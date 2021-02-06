import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";
import { ReportItem } from '../model/report.model';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {
  path = 'http://localhost:8080/api/report/';
  pathGetAll = this.path + "all";
  pathGenerate = this.path + "generate/";

  constructor(private http: HttpClient) { }

  getReports(): Observable<ReportItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathGetAll, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToReports(xml)));
  }

  generateReport(username: string){
    console.log("generate report service");
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<any>(this.pathGenerate + username, { headers: headers });
  }

  private xmlToReports(xml: string): ReportItem[] {
    console.log("parse = ", xml);
    let reportsItems: ReportItem[] = [];
    const parser = new DOMParser();
    let reportsList = parser.parseFromString(xml,"text/xml").getElementsByTagName('reportItem'); 
    //console.log(reportsList);
    for (let i = 0; i < reportsList.length; ++i){
      reportsItems.push({
        'id': reportsList.item(i).getElementsByTagName('id')[0].textContent,
        'datum': reportsList.item(i).getElementsByTagName('datum')[0].textContent,
        'zahtevBrojPodnetih': Number(reportsList.item(i).getElementsByTagName('zahtevBrojPodnetih')[0].textContent),
        'zahtevBrojUsvojenih': Number(reportsList.item(i).getElementsByTagName('zahtevBrojUsvojenih')[0].textContent),
        'zahtevBrojOdbacenih': Number(reportsList.item(i).getElementsByTagName('zahtevBrojOdbacenih')[0].textContent),
        'zahtevBrojOdbijenih': Number(reportsList.item(i).getElementsByTagName('zahtevBrojOdbijenih')[0].textContent),        
        'zalbaBrojPodnetih': Number(reportsList.item(i).getElementsByTagName('zalbaBrojPodnetih')[0].textContent),
        'zalbaBrojZbogNepostupanja': Number(reportsList.item(i).getElementsByTagName('zalbaBrojZbogNepostupanja')[0].textContent),
        'zalbaBrojZbogOdbijanja': Number(reportsList.item(i).getElementsByTagName('zalbaBrojZbogOdbijanja')[0].textContent),
      }) 
    }
    return reportsItems;
  }
  
}
