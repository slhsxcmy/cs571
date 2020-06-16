import { Component } from '@angular/core';
import { User } from './user';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'template-driven-forms';

  topics = ['Angular', 'React', 'Vue'];

  userModel = new User('Rob', 'rob@test.com', 123456789, '', 'morning', true);

}
