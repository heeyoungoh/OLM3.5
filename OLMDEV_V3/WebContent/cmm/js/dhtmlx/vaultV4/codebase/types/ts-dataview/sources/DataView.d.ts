import { ISelection } from "../../ts-list";
import { View } from "../../ts-common/view";
import { DataCollection, DataEvents, DragEvents, IDataEventsHandlersMap, IDragEventsHandlersMap } from "../../ts-data";
import { IEventSystem } from "../../ts-common/events";
import { IDataViewConfig, DataViewEvents, IDataViewEventHandlersMap, IDataView } from "./types";
export declare class DataView extends View implements IDataView {
    config: IDataViewConfig;
    data: DataCollection;
    events: IEventSystem<DataEvents | DragEvents | DataViewEvents, IDataEventsHandlersMap & IDragEventsHandlersMap & IDataViewEventHandlersMap>;
    selection: ISelection;
    private _handlers;
    private _navigationDestructor;
    private _documentClickDestuctor;
    private _focusIndex;
    private _widgetInFocus;
    private _edited;
    private _events;
    private _touch;
    constructor(node: HTMLElement | string, config?: IDataViewConfig);
    editItem(id: string): void;
    getFocusItem(): import("../../ts-data").IDataItem;
    setItemInRow(amount: any): void;
    setFocus(id: string): void;
    getFocus(): string;
    destructor(): void;
    getFocusIndex(): number;
    setFocusIndex(index: number): void;
    edit(id: string): void;
    disableSelection(): void;
    enableSelection(): void;
    private _dblClick;
    private _clearTouchTimer;
    private _dragStart;
    private _setFocusIndex;
    private _renderItem;
    private _draw;
    private _getHotkeys;
}