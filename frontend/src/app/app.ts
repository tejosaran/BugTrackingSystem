import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { BrowserModule } from '@angular/platform-browser';

import { Projectscomponent } from './components/projectscomponent/projectscomponent';
import { provideHttpClient } from '@angular/common/http';
import { Ticketscomponent } from './components/ticketscomponent/ticketscomponent';
import { Navbar } from './components/navbar/navbar';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Projectscomponent,Ticketscomponent,Navbar
    


  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('bug-tracking');
}
