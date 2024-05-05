import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { SearchComponent } from './components/search/search.component';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/signup/signup.component';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './components/user/profile.component';
import { LoginService } from './services/login.service';
import { ArtistComponent } from './components/artist/artist.component';
import { PaymentComponent } from './components/payment/payment.component';
import { IndexComponent } from './components/index/index.component';
import { NewArtistComponent } from './components/admin/newArtist.component';
import { NewConcertComponent } from './components/admin/newConcert.component';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SignUpComponent,
    ProfileComponent,
    ArtistComponent,
    SearchComponent,
    PaymentComponent,
    IndexComponent,
    NewArtistComponent,
    NewConcertComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    NgbNavModule
  ],
  providers: [LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
