import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/user/profile.component';
import { ArtistComponent } from './components/artist/artist.component';
import { PaymentComponent } from './components/payment/payment.component';
import { IndexComponent } from './components/index/index.component';
import { NewArtistComponent } from './components/admin/newArtist.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'search', component: SearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'artist/:id', component: ArtistComponent },
  { path: 'payment/:id', component: PaymentComponent },
  { path: 'profile', component: ProfileComponent},
  { path: 'newArtist', component: NewArtistComponent},
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
