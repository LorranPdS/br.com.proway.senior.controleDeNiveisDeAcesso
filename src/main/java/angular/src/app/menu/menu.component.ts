import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  private searchTerms = new Subject<string>();

  constructor() { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
  }

}
