import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DAppealItem } from "../model/decision-appeal.model";
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class DecisionAppealService {
  path = 'http://localhost:8070/api/decision-appeals/';
  pathGetAll = this.path + "all";
  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  addDecisionAppeal(appeal: string, success: Function){
    console.log("service add decision appeal = ");
    console.log(appeal);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });
    this.http.post<void>(this.path, appeal, {headers: headers})
    .pipe(map(response => response))
    .subscribe(response => {
      Swal.fire({
        title: 'Success!',
        text: 'Add decision appeal successfully!',
        icon: 'success',
        confirmButtonText: 'OK'
      });
      success();
      return true;
    }, error => {
      console.log(error);
      Swal.fire({
        title: 'Error!',
        text: 'Something went wrong! ' + error.error,
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    });
  }

  getAppeals(): Observable<DAppealItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathGetAll, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  getAppealsForUsername(username: string): Observable<DAppealItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.path + username + "/all", { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  private xmlToAppeal(xml: string): DAppealItem[] {
    console.log("parse = ", xml);
    let appealItems: DAppealItem[] = [];
    const parser = new DOMParser();
    let appealsList = parser.parseFromString(xml,"text/xml").getElementsByTagName('DAppealItem'); 
    console.log("appealsList = ", appealsList);
    for (let i = 0; i < appealsList.length; ++i){
      appealItems.push({
        'status': appealsList.item(i).getElementsByTagName('status')[0].textContent,
        'podnosiocUlica': appealsList.item(i).getElementsByTagName('podnosiocUlica')[0].textContent,
        'podnosiocGrad': appealsList.item(i).getElementsByTagName('podnosiocGrad')[0].textContent,
        'podnosiocIme': appealsList.item(i).getElementsByTagName('podnosiocIme')[0].textContent,
        'podnosiocPrezime': appealsList.item(i).getElementsByTagName('podnosiocPrezime')[0].textContent,
        'podnosiocUsername': appealsList.item(i).getElementsByTagName('podnosiocUsername')[0].textContent,
        'organVlasti': appealsList.item(i).getElementsByTagName('organVlasti')[0].textContent,
        'broj': appealsList.item(i).getElementsByTagName('broj')[0].textContent,
        'mestoSlanja': appealsList.item(i).getElementsByTagName('mestoSlanja')[0].textContent,
        'datumSlanja': appealsList.item(i).getElementsByTagName('datumSlanja')[0].textContent,
        'poverenikUsername': appealsList.item(i).getElementsByTagName('poverenikUsername')[0].textContent,
      }) 
    }
    return appealItems;
  }

  deleteAppeal(broj: string, updateTable: Function) {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    this.http.delete(this.path + broj, { headers: headers })
      .pipe(map(response => response))
      .subscribe(response => {
        updateTable();
        Swal.fire({
          title: 'Success!',
          text: 'Appeal successfully deleted!',
          icon: 'success',
          confirmButtonText: 'OK'
        });
        return true;
      }, error => {
        console.log(error);
        Swal.fire({
          title: 'Error!',
          text: 'Something went wrong! ' + error.error,
          icon: 'error',
          confirmButtonColor: '#DC143C',
          confirmButtonText: 'OK'
        });
        return false;
      })
  }
}
