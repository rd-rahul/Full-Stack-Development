import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  showLogin: boolean = true; // Default to show the login form

  toggleForm() {
    this.showLogin = !this.showLogin; // Switch between login and signup
  }
}
