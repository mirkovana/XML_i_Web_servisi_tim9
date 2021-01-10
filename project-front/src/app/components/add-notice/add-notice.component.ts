import { Component, OnInit } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { NoticeService } from '../../service/notice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NoticeDTO } from "../../model/notice.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-notice',
  templateUrl: './add-notice.component.html',
  styleUrls: ['./add-notice.component.css']
})
export class AddNoticeComponent implements OnInit {
  appeal = '';
  textArea: string;

  constructor(
    private service: NoticeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("Send file");

    let obj : NoticeDTO = {
      "text" : this.textArea
    }

    this.service.addNotice(obj).subscribe(
      response =>{
        console.log("added notice");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }

}
