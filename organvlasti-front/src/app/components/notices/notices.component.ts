import { Component, OnInit } from '@angular/core';
import { NoticeItem } from 'src/app/model/notice.model';
import { NoticeService } from '../../service/notice.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-notices',
  templateUrl: './notices.component.html',
  styleUrls: ['./notices.component.css']
})
export class NoticesComponent implements OnInit {

  notices: Array<NoticeItem> = [];

  constructor(private service: NoticeService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    console.log("get requests");
    this.getNotices();
  }

  getNotices(){
    if(this.userService.isUser()){
      console.log("isUser");
      this.service.getNoticesForUser(this.getUsername()).subscribe((data: NoticeItem[])  => {
        console.log("success");
        console.log(data);
        this.notices = data;
      }, error => {
        console.log(error);
      });
    }else if(this.userService.isAdmin()){
      console.log("isAdmin");
      this.service.getNotices().subscribe((data: NoticeItem[])  => {
        console.log(data);
        this.notices = data;
      }, error => {
        console.log(error);
      });;
    }
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
