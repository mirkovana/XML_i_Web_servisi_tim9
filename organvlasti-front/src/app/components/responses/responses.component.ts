import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { ResponseItem } from '../../model/response.model';
import { ResponseService } from '../../service/response.service';
import { UserService } from '../../service/user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-responses',
  templateUrl: './responses.component.html',
  styleUrls: ['./responses.component.css']
})
export class ResponsesComponent implements OnInit {

  responses: ResponseItem[] = [];

  myForm = new FormGroup({
    broj: new FormControl(''),
    datum: new FormControl(''),
    status: new FormControl(''),
  });

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });

  constructor(private service: ResponseService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getResponses();
  }

  getResponses() {
    if (this.isUser()) {
      console.log("getappealsforuser");
      this.service.getResponsesForUsername(this.getUsername()).subscribe((data: ResponseItem[]) => {
        console.log("component subscribe = ", data);
        this.responses = data;
      }, error => {
        console.log("error = ", error);
      });
    } else {
      console.log("getappealsforadmin");
      this.service.getResponses().subscribe((data: ResponseItem[]) => {
        console.log("component subscribe = ", data);
        this.responses = data;
      }, error => {
        console.log("error = ", error);
      });
    }
  }

  replyRequest(request: ResponseItem) {
    console.log("reply = ", request);
    this.router.navigate(['/add-notice/' + request.podnosiocUsername + "/" + request.broj]);
  }

  submit() {
    console.log("form = ", this.myForm.value);
    if (this.myForm.value.broj == ""
      && this.myForm.value.datum == ""
      && this.myForm.value.status == "") {
      this.getResponses();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
      <resenjeSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <broj>`+ this.myForm.value.broj + `</broj>
          <datum>`+ this.myForm.value.datum + `</datum>
          <status>`+ this.myForm.value.status + `</status>
      </resenjeSearch>`;

    this.service.searchByMetadata(xml).subscribe((data: any) => {
      console.log("data = ", data);
      this.responses = data;
    }, error => {
      console.log(error);
    });
  }

  submit1() {
    console.log("form = ", this.myForm1.value);
    if (this.myForm1.value.keywords == "") {
      this.getResponses();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
      <keywordSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <keywords>`+ this.myForm1.value.keywords + `</keywords>
      </keywordSearch>`;

    this.service.searchByKeywords(xml).subscribe((data: any) => {
      console.log("data = ", data);
      this.responses = data;
    }, error => {
      console.log(error);
    });

  }

  isUser() {
    return this.userService.isUser();
  }

  isAdmin() {
    return this.userService.isAdmin();
  }

  getUsername() {
    return this.userService.getUsername();
  }
}
