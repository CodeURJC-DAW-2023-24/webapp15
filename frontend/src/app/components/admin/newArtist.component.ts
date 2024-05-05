
import { Component, ViewChild } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { newArtistService } from '../../services/newArtist.service';
import { Artist } from '../../models/artist.model';
import { Router } from '@angular/router';

@Component({
  selector: 'newArtist',
  templateUrl: 'newArtist.component.html',
  styleUrl: 'newArtist.component.css'
})
export class NewArtistComponent {
  constructor(private http: HttpClient, private newArtist: newArtistService, private router: Router) { }
  name:string ='';
  info:string ='';
  image: File | undefined;
  imageSrc:string|undefined;

 
  @ViewChild("file")
  file: any;
  
  onSubmit(){
    const requestBody = {"name": this.name, "info": this.info}
    this.newArtist.createArtist(requestBody).subscribe({
      next: (v: Artist) => {
          this.uploadImage(v)
          this.router.navigate(['/']);
      },
      error: (e: HttpErrorResponse) => {
          
      }
  })
  }
  uploadImage(artist: Artist) {
    const image = this.file.nativeElement.files[0];
    if (image) {
      const data = new FormData();
      data.append('imageFile', image);
      this.newArtist.setArtistImage(artist, data).subscribe();
    }
  }
  functionChangeImage(event: any) {
        const reader = new FileReader();
        reader.onload = (event: any) => {
          this.imageSrc = event.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      
    }
    
}  
