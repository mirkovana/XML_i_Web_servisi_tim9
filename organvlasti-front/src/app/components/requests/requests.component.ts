import { Component, OnInit } from '@angular/core';
import { RequestItem } from 'src/app/model/request.model';
import { RequestService } from '../../service/request.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  requests: Array<RequestItem> = [];

  constructor(private service: RequestService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    console.log("get requests");
    this.getRequests();
  }

  getRequests(){
    if(this.userService.isUser()){
      console.log("isUser");
      this.service.getRequestsForUser(this.getUsername()).subscribe((data: RequestItem[])  => {
        console.log("success");
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });
    }else if(this.userService.isAdmin()){
      console.log("isAdmin");
      this.service.getRequests().subscribe((data: RequestItem[])  => {
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });;
    }
  }

  showNotice(request: RequestItem){
    
  }

  replyRequest(request: RequestItem){
    console.log("reply = ", request);
    this.router.navigate(['/add-notice/'+request.username+"/"+request.broj]);
  }

  denyRequest(request: RequestItem){
    console.log("deny = ", request);
    this.service.denyRequest(request.broj).subscribe((data: any)  => {
      console.log(data);
      console.log("request denied");
      this.getRequests();
    }, error => {
      //TODO ovde nekako uvek baca gresku iako je promena zahteva u bazi uspesna
      this.getRequests();
      console.log(error);
    });
  }

  convertTimestamp(time: string): string {
    let date = new Date(parseInt(time));
    return date.toLocaleString();
  }

  deleteRequest(request: RequestItem){
    console.log("delete = ", request);
    this.service.deleteRequest(request.broj, ()=> {
      this.requests = this.requests.filter(item => item.broj != request.broj);
    });
  }

  isUser(){
    return this.userService.isUser();
  }
  
  isAdmin(){
    return this.userService.isAdmin();
  }

  getUsername(){
    return this.userService.getUsername();
  }
}
