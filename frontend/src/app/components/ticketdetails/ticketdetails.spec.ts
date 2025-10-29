import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ticketdetails } from './ticketdetails';

describe('Ticketdetails', () => {
  let component: Ticketdetails;
  let fixture: ComponentFixture<Ticketdetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Ticketdetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ticketdetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
