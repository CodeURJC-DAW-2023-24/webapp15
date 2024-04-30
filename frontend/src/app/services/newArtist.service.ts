import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class newArtistService {
  
  constructor(private http: HttpClient) { }

  createArtist(name:string,info:string,image:File | undefined) {
    
    const requestBody = {"name": name, "info": info,"image":image}
    return this.http.post("/api/artists", requestBody)
            .subscribe((response: any) => {
                console.log(response);
            })
  }
}
