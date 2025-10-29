import { TestBed } from '@angular/core/testing';

import { Ticketsservice } from './ticketsservice';

describe('Ticketsservice', () => {
  let service: Ticketsservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Ticketsservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
