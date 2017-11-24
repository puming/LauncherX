/**
 * Created by pm on 17-10-27.
 */
import * as TYPES from '../actions/Types';

const initialState = {
  ready: false,
  videoUri: '',
  data: {},
};
export default function fn(state = initialState, action = {}) {
  switch (action.type) {
    case TYPES.FETCH_DETAILS_PAGE:

      return Object.assign({}, state, {
        ready: action.ready,
        videoUri: action.videoUri,
        data: action.data,
      });
    case TYPES.FETCH_DETAILS_VIDEO_URL:
      return Object.assign({}, state, {});
    default:
      return initialState
  }
}