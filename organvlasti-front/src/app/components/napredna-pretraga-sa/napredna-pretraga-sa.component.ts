import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SAppealItem } from 'src/app/model/silence-appeal.model';
import { SilenceAppealService } from 'src/app/service/silence-appeal.service';

@Component({
  selector: 'app-napredna-pretraga-sa',
  templateUrl: './napredna-pretraga-sa.component.html',
  styleUrls: ['./napredna-pretraga-sa.component.css']
})
export class NaprednaPretragaSaComponent implements OnInit {

  naprednaPretraga: FormGroup;
  organVlasti: string = '';
  broj: string = '';
  ime: string = '';
  prezime: string = '';
  datum: string = '';
  mesto: string = '';
  zalbe: SAppealItem[] = new Array();

  constructor(private formBuilder: FormBuilder,private service:SilenceAppealService) { }

  ngOnInit(): void {
    this.naprednaPretraga = this.formBuilder.group({
      organVlasti: [this.organVlasti, Validators.required],
      ime: [this.ime, Validators.required],
      prezime: [this.prezime, Validators.required],
      datum: [this.datum, Validators.required],
      broj: [this.broj, Validators.required],
      mesto: [this.mesto, Validators.required]
    })
  }

  onSubmit(){
    this.service.naprednaPretraga(
      this.naprednaPretraga.get("organVlasti").value, 
      this.naprednaPretraga.get("mesto").value,
      this.naprednaPretraga.get("ime").value, 
      this.naprednaPretraga.get("prezime").value, 
      this.naprednaPretraga.get("datum").value, 
      this.naprednaPretraga.get("broj").value
      ).subscribe(data=>{
        console.log(data);
        this.zalbe = data;
        for (let index = 0; index < data.length; index++) {
         let ob:SAppealItem = data[index];
         console.log(ob);
          
        }
      });
  }

}
