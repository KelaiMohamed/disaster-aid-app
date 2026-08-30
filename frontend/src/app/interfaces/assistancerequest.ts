import { Skills } from './skills';
import { Aidtype } from './aidtype';
import { Location } from './location';

export interface Assistancerequest {
  id?: string;
  state: 'PENDING' | 'APPROVED' | 'REJECTED';
  date: string;
  userId: string;
  skills: Skills[];
  aidType: Aidtype[];
  location: Location;
  description: string;
}
