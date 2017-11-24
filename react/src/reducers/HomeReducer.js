/**
 * Created by pm on 17-10-12.
 */
import * as TYPES from '../actions/Types';
import {handleActions} from 'redux-actions';

/*export default handleActions(
 {
 [TYPES.FETCH_MOVE_LIST]: {
 next(state, action) {
 for(action in action.payload){
 console.log("action="+action);
 }
 return {ret: true, data: action.payload};
 },
 throw(state, action) {
 return {ret: false, statusText: action.payload, data: []};
 }
 }
 },
 {ret: true, statusText: '', data: []});*/
const initialState = {
  isFetch: false,
  json:{},
  data: [],
};
const defaultAction = {};

/**
 *接收先前的 state 和 action，并返回新的 state
 * @param state
 * @param action
 * @returns {*}
 */
export default function getNewState(state = initialState, action = defaultAction) {
  switch (action.type) {
    case TYPES.FETCH_MOVE_LIST:
      for(s in state){
        console.log("HomeReducer:"+"state="+s);
      }
      let data=[];
      return Object.assign({}, state, {
        isFetch: true,
        json:action.movie,
        data: state.data.concat(action.movie.subjects),
      });
    case TYPES.FETCH_FIND_LIST:
      return {
        ...state,
        isFetch: false,
        json:{},
        data: [],
      };
    default:
      return state;
  }
}