import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Notice } from 'src/app/model/notice.model';
import { NoticeService } from 'src/app/service/notice.service';

@Component({
  selector: 'app-napredna-pretraga',
  templateUrl: './napredna-pretraga.component.html',
  styleUrls: ['./napredna-pretraga.component.css']
})
export class NaprednaPretragaComponent implements OnInit {

  naprednaPretraga: FormGroup;
  nazivOrgana: string = '';
  sedisteOrgana: string = '';
  ime: string = '';
  prezime: string = '';
  datum: string = '';
  brojPredmeta: string = '';
  obavestenja: Notice[] = new Array();



  constructor(
    private formBuilder: FormBuilder,
    private noticeService: NoticeService
  ) { }


  ngOnInit(): void {
    this.naprednaPretraga = this.formBuilder.group({
      nazivOrgana: [this.nazivOrgana, Validators.required],
      sedisteOrgana: [this.sedisteOrgana, Validators.required],
      ime: [this.ime, Validators.required],
      prezime: [this.prezime, Validators.required],
      datum: [this.datum, Validators.required],
      brojPredmeta: [this.brojPredmeta, Validators.required]
    })
  }

  // saveChangesEnabled() {
  //   return this.naprednaPretraga.get("nazivOrgana").value.length > 0  
  //   && this.naprednaPretraga.get("sedisteOrgana").value.length > 0 
  //   && this.naprednaPretraga.get("ime").value.length > 0
  //   && this.naprednaPretraga.get("prezime").value.length > 0
  //   && this.naprednaPretraga.get("datum").value.length > 0
  //   && this.naprednaPretraga.get("brojPredmeta").value.length > 0
  // }

  onSubmit(){
    this.noticeService.naprednaPretraga(
      this.naprednaPretraga.get("nazivOrgana").value, 
      this.naprednaPretraga.get("sedisteOrgana").value,
      this.naprednaPretraga.get("ime").value, 
      this.naprednaPretraga.get("prezime").value, 
      this.naprednaPretraga.get("datum").value, 
      this.naprednaPretraga.get("brojPredmeta").value
      ).subscribe(data=>{
        console.log(data);
        this.obavestenja = data;
        for (let index = 0; index < data.length; index++) {
         let ob:Notice = data[index];
         console.log(ob);
          
        }
      });
  }
}
