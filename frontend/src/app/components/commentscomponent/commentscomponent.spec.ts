import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Commentscomponent } from './commentscomponent';

describe('Commentscomponent', () => {
  let component: Commentscomponent;
  let fixture: ComponentFixture<Commentscomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Commentscomponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Commentscomponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
