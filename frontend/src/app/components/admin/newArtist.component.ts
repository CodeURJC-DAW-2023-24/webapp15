
import { Component, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { newArtistService } from '../../services/newArtist.service';

@Component({
  selector: 'newArtist',
  templateUrl: 'newArtist.component.html',
  styleUrl: 'newArtist.component.css'
})
export class NewArtistComponent {
  constructor(private http: HttpClient, private newArtist: newArtistService) { }
  name:string ='';
  info:string ='';
  image: File | undefined;

 
  @ViewChild("file")
  file: any;
  
  onSubmit(){
    return this.newArtist.createArtist(this.name,this.info,this.image);
  }
  onFileSelected(event: any) {
      this.image= event.target.files[0];
}
}