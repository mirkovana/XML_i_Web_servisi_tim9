import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SAppealItem } from "../model/silence-appeal.model";

import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class SilenceAppealService {

  path = 'http://localhost:8070/api/silence-appeals/';
  pathGetAll = this.path + 'all';
  pathRequestExplanation = this.path + 'requestExplanation/'
  pathSearch = this.path + 'search';
  pathSearchKeywords = this.path + "keywords";

  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  naprednaPretraga(organVlasti: string, mesto: string, ime: string,
    prezime: string, datum: string, broj: string) {

    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    const formData = new FormData();
    formData.append('organVlasti', organVlasti);
    formData.append('mesto', mesto);
    formData.append('ime', ime);
    formData.append('prezime', prezime);
    formData.append('datum', datum);
    formData.append('broj', broj);
    console.log(localStorage.getItem("token"))
    return this.http.post<any>(this.path + 'napredna-pretraga', formData, {headers: headers, });
  }

  addSilenceAppeal(appeal: string, success: Function) {
    console.log("service add silence appeal = ");
    console.log(appeal);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', 
      'Accept': 'application/xml',       
      'Response-Type': 'text'
    });
    this.http.post<void>(this.path, appeal, {headers: headers})
      .pipe(map(response => response))
      .subscribe(response => {
        Swal.fire({
          title: 'Success!',
          text: 'Add silence appeal successfully!',
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
      })
  }

  getAppeals(): Observable<SAppealItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathGetAll, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  requestExplanation(broj: string): Observable<string>{
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathRequestExplanation + broj, { headers: headers });
  }

  getAppealsForUsername(username: string): Observable<SAppealItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.path + username + "/all", { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  searchByKeywords(xml: string): Observable<SAppealItem[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml',
      'Accept': 'application/xml',      
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearchKeywords, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  searchByMetadata(xml: string): Observable<SAppealItem[]>{
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', 
      'Accept': 'application/xml',       
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearch, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToAppeal(xml)));
  }

  private xmlToAppeal(xml: string): SAppealItem[] {
    console.log("parse = ", xml);
    let appealItems: SAppealItem[] = [];
    const parser = new DOMParser();
    let appealsList = parser.parseFromString(xml,"text/xml").getElementsByTagName('SAppealItem'); 
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
        'razlog': appealsList.item(i).getElementsByTagName('razlog')[0].textContent,
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
