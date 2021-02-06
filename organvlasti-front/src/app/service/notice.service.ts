import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NoticeItem } from '../model/notice.model';
import { map } from 'rxjs/operators';
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})

export class NoticeService {
  path = 'http://localhost:8080/api/notice/';
  pathAll = this.path+ "all"
  pathSearch = this.path + "search";
  pathSearchKeywords = this.path + "keywords";

  /*headers: HttpHeaders = new HttpHeaders({
    Authorization: 'Bearer ' + localStorage.getItem('token'),
    'Content-Type': 'application/xml', //<- To SEND XML
    //'Accept': 'application/xml',       //<- To ask for XML
    //'Response-Type': 'text'             //<- b/c Angular understands text
  });*/

  constructor(private http: HttpClient) { }

  addNotice(notice: string, success: Function) {
    console.log("service add notice = ");
    console.log(notice);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml', //<- To SEND XML
      'Accept': 'application/xml',       //<- To ask for XML
      'Response-Type': 'text'
    });
    this.http.post<void>(this.path, notice, { headers: headers })
      .pipe(map(response => response))
      .subscribe(response => {
        Swal.fire({
          title: 'Success!',
          text: 'Obavestenje poslato!',
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

  naprednaPretraga(nazivOrgana: string, sedisteOrgana: string, ime: string,
    prezime: string, datum: string, brojPredmeta: string) {

      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem("token"),
        // 'Content-Type': 'application/xml',
        'Accept': 'application/xml',      
        'Response-Type': 'text'
      });
    const formData = new FormData();
    formData.append('nazivOrgana', nazivOrgana);
    formData.append('sedisteOrgana', sedisteOrgana);
    formData.append('ime', ime);
    formData.append('prezime', prezime);
    formData.append('datum', datum);
    formData.append('brojPredmeta', brojPredmeta);
    console.log(localStorage.getItem("token"))
    return this.http.post<any>(this.path + 'napredna-pretraga', formData, {headers: headers,responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToNotice(xml)));
  }

  getNoticesForUser(username: string): Observable<NoticeItem[]> {
    console.log("getforuser = ", username);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
    });
    return this.http.get<string>(this.path + username + "/user/all", {
      headers: headers,
      responseType: 'text' as 'json'
    })
      .pipe(map((xml: string) => this.xmlToNotice(xml)));
  }

  getNoticesForOrganVlasti(username: string): Observable<NoticeItem[]> {
    console.log("getForOrganVlasti = ", username);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
    });
    return this.http.get<string>(this.path + username + "/organvlasti/all", {
      headers: headers,
      responseType: 'text' as 'json'
    })
      .pipe(map((xml: string) => this.xmlToNotice(xml)));
  }

  getNotices(): Observable<NoticeItem[]> {
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") });
    return this.http.get<string>(this.pathAll, { headers: headers, responseType: 'text' as 'json' })
      .pipe(map((xml: string) => this.xmlToNotice(xml)));
  }

  searchByKeywords(xml: string): Observable<NoticeItem[]>{
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("token"),
      'Content-Type': 'application/xml',
      'Accept': 'application/xml',      
      'Response-Type': 'text'
    });
    return this.http.post<string>(this.pathSearchKeywords, xml, { headers: headers, responseType: 'text' as 'json' })
    .pipe(map((xml: string) => this.xmlToNotice(xml)));
  }

  // searchByMetadata(xml: string): Observable<NoticeItem[]>{
  //   const headers = new HttpHeaders({
  //     'Authorization': 'Bearer ' + localStorage.getItem("token"),
  //     'Content-Type': 'application/xml', 
  //     'Accept': 'application/xml',       
  //     'Response-Type': 'text'
  //   });
  //   return this.http.post<string>(this.pathSearch, xml, { headers: headers, responseType: 'text' as 'json' })
  //   .pipe(map((xml: string) => this.xmlToNotice(xml)));
  // }

  private xmlToNotice(xml: string): NoticeItem[] {
    console.log("parse = ", xml);
    let noticeItems: NoticeItem[] = [];
    const parser = new DOMParser();
    let noticesList = parser.parseFromString(xml, "text/xml").getElementsByTagName('noticeItem');
    console.log(noticesList);
    for (let i = 0; i < noticesList.length; ++i) {
      noticeItems.push({
        'broj': noticesList.item(i).getElementsByTagName('broj')[0].textContent,
        'username': noticesList.item(i).getElementsByTagName('username')[0].textContent,
        'datum': noticesList.item(i).getElementsByTagName('datum')[0].textContent,
        'organVlastiUsername': noticesList.item(i).getElementsByTagName('organVlastiUsername')[0].textContent,
        'nazivOrgana': noticesList.item(i).getElementsByTagName('nazivOrgana')[0].textContent,
        'sedisteOrgana': noticesList.item(i).getElementsByTagName('sedisteOrgana')[0].textContent,
        'imePodnosioca': noticesList.item(i).getElementsByTagName('imePodnosioca')[0].textContent,
        'prezimePodnosioca': noticesList.item(i).getElementsByTagName('prezimePodnosioca')[0].textContent,
        'iznos': noticesList.item(i).getElementsByTagName('iznos')[0].textContent,
      });
    }
    return noticeItems;
  }

  getHTML() {
    return this.http.get<void>(this.path + '/api/notice/html/123abc');
  }
}
