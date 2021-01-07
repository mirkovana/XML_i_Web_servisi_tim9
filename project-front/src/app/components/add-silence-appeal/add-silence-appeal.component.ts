import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SilenceAppealDTO } from "../../model/silence-appeal.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-silence-appeal',
  templateUrl: './add-silence-appeal.component.html',
  styleUrls: ['./add-silence-appeal.component.css']
})
export class AddSilenceAppealComponent implements OnInit {
  appeal = '';
  textArea: string;

  constructor(private service: SilenceAppealService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("sendFile");
    
    let obj: SilenceAppealDTO = {
      "text" : this.textArea
    };
    console.log(obj);
    this.service.addSilenceAppeal(obj).subscribe(
      response => {
        console.log("added appeal");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }
}
