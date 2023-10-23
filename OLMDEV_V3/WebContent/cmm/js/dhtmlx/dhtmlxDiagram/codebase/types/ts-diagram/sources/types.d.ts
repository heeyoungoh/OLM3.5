export { SelectionEvents } from "../../ts-common/types";
export { DataEvents } from "../../ts-data";
import { VNode } from "../../ts-common/dom";
import { IEventSystem } from "../../ts-common/events";
import { Id, ISelectionEventsHandlersMap, SelectionEvents } from "../../ts-common/types";
import { DataEvents, IDataItem } from "../../ts-data";
import { Line } from "./components/Lines/Line";
import { LineText } from "./components/Lines/LineText";
import { CellManager, CellManagerEvents, ICellManagerHandlersMap } from "./helpers/CellManager";
import { Editor, EditorEvents } from "./helpers/Editor";
import { Exporter } from "./helpers/Export";
import { Selection } from "./helpers/Selection";
import { ShapesCollection } from "./ShapesCollection";
import { Toolbar } from "./Toolbar";
export declare type DiagramType = "default" | "org" | "mindmap";
export declare type ItemType = "base" | "shape" | "line" | "group" | "swimlane";
export declare type BaseDirection = "top" | "bottom" | "left" | "right";
export declare type LineShapeSide = BaseDirection | "center" | string;
export declare type LineDirection = "vertical" | "verticalLeft" | "verticalRight";
export declare type ActionDirection = "up" | "down" | "left" | "right";
export declare type TreeDirection = "right" | "left";
export declare type ICellType = "row" | "col";
export declare type ActionValidate = "move" | "remove" | "add";
export declare type TextAlign = "left" | "center" | "right";
export declare type TextVerticalAlign = "top" | "center" | "bottom";
export declare type StrokeType = "line" | "dash" | string;
export declare type Arrows = "filled" | string;
export declare type ArrowType = "forwardArrow" | "backArrow" | string;
export declare type ConnectType = "straight" | "flex" | "elbow" | "curved";
export declare type FontStyle = "normal" | "italic" | "oblique";
export declare type ItemConfig = IGroupConfig | ISwimlaneConfig | IOrgCardConfig | IImgOrgCardConfig | ITopicShapeConfig | ITextShapeConfig | IFlowShapeConfig | ICustomShapeConfig | ILineConfig;
export declare type ShapeConfig = IOrgCardConfig | IImgOrgCardConfig | ITopicShapeConfig | ITextShapeConfig | IFlowShapeConfig | ICustomShapeConfig;
export interface IDiagram {
    version: string;
    config: IDiagramConfig;
    events: IEventSystem<DataEvents | SelectionEvents | DiagramEvents | EditorEvents | CellManagerEvents, IDiagramEventHandlersMap | ICellManagerHandlersMap | ISelectionEventsHandlersMap>;
    data: ShapesCollection;
    selection: Selection;
    cellManager: CellManager;
    export: Exporter;
    toolbar: Toolbar;
    editor: Editor;
    paint(): void;
    destructor(): void;
    locate(event: Event): IBaseShape;
    collapseItem(id: Id, dir?: TreeDirection): void;
    expandItem(id: Id, dir?: TreeDirection): void;
    getScrollState(): ICoords;
    scrollTo(x: number, y: number): void;
    showItem(id: Id): void;
    addShape(type: string, parameters: ICustomShapeParam): void;
    autoPlace(config?: IAutoPlacement): void;
}
export interface IDiagramConfig {
    type?: DiagramType;
    typeConfig?: IMindMapConfig;
    defaultShapeType?: string;
    lineConfig?: ILineConfig;
    defaults?: IDefaultShapeConfig;
    properties?: {
        [key: string]: string;
    }[];
    margin?: IMarginConfig;
    toolbar?: IShapeToolbarConfig[];
    select?: boolean;
    scroll?: boolean;
    scale?: number;
    gridStep?: number;
    lineGap?: number;
    autoplacement?: IAutoPlacement;
    exportStyles?: boolean | string[];
    $svg?: any;
    /** @deprecated See a documentation: https://docs.dhtmlx.com/ */
    defaultLinkType?: StrokeType;
}
export declare enum DiagramEvents {
    scroll = "scroll",
    beforeRender = "beforerender",
    emptyAreaClick = "emptyAreaClick",
    emptyAreaDblClick = "emptyAreaDblClick",
    emptyAreaMouseDown = "emptyAreaMouseDown",
    beforeSubmenuOpen = "beforeSubmenuOpen",
    afterSubmenuOpen = "afterSubmenuOpen",
    beforeCollapse = "beforeCollapse",
    afterCollapse = "afterCollapse",
    beforeExpand = "beforeExpand",
    afterExpand = "afterExpand",
    shapeMouseDown = "shapeMouseDown",
    shapeClick = "shapeClick",
    shapeDblClick = "shapeDblClick",
    shapeIconClick = "shapeIconClick",
    lineMouseDown = "lineMouseDown",
    lineClick = "lineClick",
    lineDblClick = "lineDblClick",
    lineTitleMouseDown = "lineTitleMouseDown",
    lineTitleClick = "lineTitleClick",
    lineTitleDblClick = "lineTitleDblClick",
    groupMouseDown = "groupMouseDown",
    groupClick = "groupClick",
    groupDblClick = "groupDblClick",
    groupHeaderClick = "groupHeaderClick",
    groupHeaderDblClick = "groupHeaderDblClick",
    itemMouseDown = "itemMouseDown",
    itemClick = "itemClick",
    itemDblClick = "itemDblClick",
    itemMouseOver = "itemMouseOver",
    itemMouseOut = "itemMouseOut",
    /** @deprecated See a documentation: https://docs.dhtmlx.com/ */
    shapeHover = "shapeHover"
}
export interface IDiagramEventHandlersMap {
    [key: string]: (...args: any[]) => any;
    [DiagramEvents.scroll]: (position: ICoords) => void;
    [DiagramEvents.beforeRender]: (size: IContentSize) => void;
    [DiagramEvents.emptyAreaClick]: (event: MouseEvent) => void;
    [DiagramEvents.emptyAreaDblClick]: (event: MouseEvent) => void;
    [DiagramEvents.emptyAreaMouseDown]: (event: MouseEvent) => void;
    [DiagramEvents.shapeIconClick]: (id: string, event: MouseEvent) => void;
    [DiagramEvents.beforeSubmenuOpen]: (id: Id, event: MouseEvent, subHeaderId?: string) => boolean | void;
    [DiagramEvents.afterSubmenuOpen]: (id: Id, event: MouseEvent, subHeaderId?: string) => void;
    [DiagramEvents.beforeCollapse]: (id: Id, dir?: TreeDirection) => boolean | void;
    [DiagramEvents.afterCollapse]: (id: Id, dir?: TreeDirection) => void;
    [DiagramEvents.beforeExpand]: (id: Id, dir?: TreeDirection) => boolean | void;
    [DiagramEvents.afterExpand]: (id: Id, dir?: TreeDirection) => void;
    [DiagramEvents.shapeMouseDown]: (id: Id, event: MouseEvent, position?: ICoords) => void;
    [DiagramEvents.shapeClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.shapeDblClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.lineMouseDown]: (id: Id, event: MouseEvent, position?: ICoords) => void;
    [DiagramEvents.lineClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.lineDblClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.lineTitleMouseDown]: (lineId: Id, titleId: Id, event: MouseEvent, position?: ICoords) => void;
    [DiagramEvents.lineTitleClick]: (lineId: Id, titleId: Id, event: MouseEvent) => void;
    [DiagramEvents.lineTitleDblClick]: (lineId: Id, titleId: Id, event: MouseEvent) => void;
    [DiagramEvents.groupMouseDown]: (id: Id, event: MouseEvent, position?: ICoords) => void;
    [DiagramEvents.groupClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.groupDblClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.groupHeaderClick]: (id: Id, event: MouseEvent, subHeaderId?: string) => void;
    [DiagramEvents.groupHeaderDblClick]: (id: Id, event: MouseEvent, subHeaderId?: string) => void;
    [DiagramEvents.itemMouseDown]: (id: Id, event: MouseEvent, position?: ICoords) => void;
    [DiagramEvents.itemClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.itemDblClick]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.itemMouseOver]: (id: Id, event: MouseEvent) => void;
    [DiagramEvents.itemMouseOut]: (id: Id, event: MouseEvent) => void;
    /** @deprecated See a documentation: https://docs.dhtmlx.com/ */
    [DiagramEvents.shapeHover]: (id: Id, event: MouseEvent) => void;
}
export interface IMindMapConfig {
    direction?: "left" | "right";
    side?: {
        left?: string[];
        right?: string[];
    };
}
export interface ILineConfig {
    lineType?: StrokeType;
    arrowsHidden?: boolean;
    lineDirection?: ArrowType;
}
export interface IDefaultShapeConfig {
    [type: string]: ShapeConfig;
}
export interface IMarginConfig {
    x?: number;
    y?: number;
    itemX?: number;
    itemY?: number;
}
export interface IShapeToolbarConfig {
    content: string | VNode;
    id: string;
    check?: Function;
    css?: Function;
}
export interface IAutoPlacement {
    root: Id;
    mode?: "direct" | "edges";
    itemPadding?: number;
    levelPadding?: number;
    graphPadding?: number;
    wide?: string;
    direction?: string;
}
export interface IBaseCoords {
    x: number;
    y: number;
}
export interface ICoords extends IBaseCoords {
    side?: string;
    $custom?: boolean;
    $rx?: number;
    $ry?: number;
}
export interface ICustomShapeParam {
    defaults?: ICustomShapeConfig;
    properties?: any[];
    template: (config: ICustomShapeConfig) => string;
    eventHandlers?: {
        [key: string]: any;
    };
}
export interface IBaseItemConfig {
    id?: Id;
    type?: string;
    x?: number;
    y?: number;
    width?: number;
    height?: number;
    hidden?: boolean;
    $group?: Id;
    $gx?: number;
    $gy?: number;
    [key: string]: any;
}
export interface IBaseItem {
    config: IBaseItemConfig;
    id: Id;
    isLocate(shape: IMeasuredItem): boolean;
    isFixed(): boolean;
    getBaseType(): ItemType;
    canResize(): boolean;
    getBox(): IBoxSize;
    render(): VNode;
    getEditorNode(): VNode;
    getMetaInfo(): {
        [key: string]: string;
    }[];
    setEditorNode(editor: VNode): void;
    isEditable(subheaderId?: string): boolean;
    destructor(): void;
}
export interface ILineConfig extends IBaseItemConfig {
    type?: StrokeType;
    connectType?: ConnectType;
    dir?: LineDirection;
    from?: string;
    to?: string;
    points?: ICoords[];
    backArrow?: Arrows;
    forwardArrow?: Arrows;
    fromSide?: LineShapeSide;
    toSide?: LineShapeSide;
    cornersRadius?: number;
    strokeWidth?: number;
    strokeType?: StrokeType;
    stroke?: string;
    customGap?: number;
    title?: ILineTitleConfig;
    $selectedPoint?: string;
    $editable?: boolean;
    $blockSelected?: boolean;
    $move?: boolean;
    $connectMode?: boolean;
    $item?: Line;
    $selected?: boolean;
    $subSelected?: Id;
}
export interface ILineTitleConfig {
    fontSize?: number | string;
    lineHeight?: number | string;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
    fontStyle?: FontStyle;
    fontColor?: string;
    fontWeight?: string;
    fill?: string;
    editable?: boolean;
    hidden?: boolean;
    draggable?: boolean;
    autoPosition?: boolean;
    text: ILineTextConfig[];
}
export interface ILineTextConfig {
    id?: Id;
    type?: "$linetext";
    width?: number;
    height?: number;
    distance?: number;
    autoPosition?: boolean;
    hidden?: boolean;
    editable?: boolean;
    draggable?: boolean;
    fill?: string;
    text?: string;
    fontSize?: number | string;
    lineHeight?: number | string;
    fontStyle?: FontStyle;
    fontColor?: string;
    fontWeight?: string;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
    $x?: number;
    $y?: number;
    $gx?: number;
    $gy?: number;
    $angle?: number;
    $editable?: boolean;
    $move?: boolean;
    $item?: LineText;
    $selected?: boolean;
}
export interface ILineText extends IBaseShape {
    config: ILineTextConfig;
    parent: ILineConfig;
}
export interface IGroupStyle {
    strokeWidth?: string | number;
    stroke?: string;
    borderStyle?: string;
    fill?: string;
    overFill?: string;
    partiallyFill?: string;
}
export interface IGroupHeader {
    height?: number | string;
    fill?: string;
    text?: string;
    fontSize?: number | string;
    lineHeight?: number | string;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
    fontStyle?: FontStyle;
    fontColor?: string;
    fontWeight?: string;
    iconColor?: string;
    position?: BaseDirection;
    editable?: boolean;
    closable?: boolean;
    enable?: boolean;
}
export interface IEntryArea {
    catchArea: number;
    borderFlexible: boolean;
}
export interface IExitArea {
    groupBehavior: "unbound" | "boundNoBorderExtension" | "boundBorderExtension";
    padding?: number;
}
export interface IBaseGroupConfig extends IBaseItemConfig {
    groupChildren?: Id[];
    style?: IGroupStyle;
    header?: IGroupHeader;
    entryArea?: IEntryArea;
    exitArea?: IExitArea;
    open?: boolean;
    $item?: IGroup;
    $minBox?: IBoxSize;
    $editable?: boolean;
    $height?: number;
    $width?: number;
    $captureArea?: "over" | "out" | "partially";
}
export interface IGroupConfig extends IBaseGroupConfig {
    type: "$group";
}
export interface IGroup extends IBaseGroup {
    config: IGroupConfig;
}
export interface IBaseGroup extends IBaseItem {
    getLocatePercent(box: IBoxSize): number;
    trackChildMove(box: IBoxSize, mov: IBaseCoords): boolean;
    getChildBox(): IBoxSize;
    setMinBox(box?: IBoxSize): void;
}
export interface IGroupSwimlaneConfig extends IBaseGroupConfig {
    type: "$sgroup";
    $borderPosition?: {
        left: boolean;
        top: boolean;
    };
    $rowCount?: number;
    $colCount?: number;
}
export interface IGroupSwimlane extends IBaseGroup {
    config: IGroupSwimlaneConfig;
}
export interface IBaseSubHeaderConfig {
    fill?: string;
    fontSize?: number | string;
    lineHeight?: number | string;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
    fontStyle?: FontStyle;
    fontColor?: string;
    fontWeight?: string;
    iconColor?: string;
    editable?: boolean;
}
export interface ISubHeaderConfig extends IBaseSubHeaderConfig {
    id?: string;
    text: string;
    $subMenu?: ISubMenuConfig;
    $movePermit?: boolean;
}
export interface ISubHeaderConfigRows extends IBaseSubHeaderConfig {
    height?: number | string;
    position?: "left" | "right";
    enable?: boolean;
    headers?: ISubHeaderConfig[];
}
export interface ISubHeaderConfigCols extends IBaseSubHeaderConfig {
    height?: number | string;
    position?: "top" | "bottom";
    enable?: boolean;
    headers?: ISubHeaderConfig[];
}
export interface ISubMenuConfig {
    enable: boolean;
    data: IDataItem[];
}
export interface ISwimlaneConfig extends IBaseGroupConfig {
    type: "$swimlane";
    layout: Id[][];
    subHeaderRows?: ISubHeaderConfigRows;
    subHeaderCols?: ISubHeaderConfigCols;
}
export interface ISwimlane extends IBaseGroup {
    config: ISwimlaneConfig;
    isEditable(subheaderId: string): boolean;
}
export interface IOrgSpecialItemConfig {
    open?: boolean;
    dx?: number;
    dy?: number;
    parent?: string;
    assistant?: boolean;
    partner?: boolean;
    catchItem?: boolean;
    giveItem?: boolean;
    $target?: boolean;
    $expandColor?: string;
    $count?: number;
    $parent?: string;
    $level?: number;
    $assistants?: Id[];
    $partners?: {
        common: Id[];
        leftHeight?: number;
        rightHeight?: number;
    };
    $even?: boolean;
    $width?: number;
    $height?: number;
    $baseHeight?: number;
}
export interface IMindMapSpecialItemConfig {
    parent?: string;
    open?: boolean;
    dx?: number;
    dy?: number;
    openDir?: {
        left?: boolean;
        right?: boolean;
    };
    catchItem?: boolean;
    giveItem?: boolean;
    $target?: boolean;
    $expandColor?: string;
    $count?: number;
    $parent?: string;
    $level?: number;
}
export interface IBaseShapeConfig extends IBaseItemConfig, IOrgSpecialItemConfig, IMindMapSpecialItemConfig {
    css?: string;
    angle?: number;
    preview?: string | IPreview;
    editable?: boolean;
    $editable?: boolean;
    $connection?: string[][];
    $selected?: boolean;
    $blockSelected?: boolean;
    $move?: boolean;
    $connectMode?: boolean;
    $item?: IBaseShape;
}
export interface IBaseShape extends IBaseItem {
    config: IBaseShapeConfig;
    getPoint(x: number, y: number): ICoords;
    getCenter(): ICoords;
}
export interface IFlowShapeConfig extends IBaseShapeConfig {
    fill?: string;
    text?: string;
    fontColor?: string;
    fontStyle?: FontStyle;
    fontWeight?: string;
    fontSize?: number;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
    lineHeight?: number;
    stroke?: string;
    extraLinesStroke?: string;
    strokeWidth?: number;
    strokeType?: StrokeType;
    strokeDash?: string;
}
export interface ICustomShapeConfig extends IBaseShapeConfig {
    [key: string]: any;
}
export interface IOrgCardConfig extends IBaseItemConfig {
    text?: string;
    headerColor?: string;
}
export interface IImgOrgCardConfig extends IOrgCardConfig {
    title?: string;
    img?: string;
}
export interface ITextShapeConfig extends IBaseShapeConfig {
    backgroundColor?: string;
    text?: string;
    fontColor?: string;
    fontStyle?: FontStyle;
    fontWeight?: string;
    fontSize?: number;
    lineHeight?: number;
    textAlign?: TextAlign;
    textVerticalAlign?: TextVerticalAlign;
}
export declare type ITopicShapeConfig = IFlowShapeConfig;
export interface IPreview {
    img?: string;
    scale?: number;
    width?: number | string;
    height?: number | string;
    gap?: number | string;
}
export interface IMeasuredItem extends IBaseCoords {
    width: number;
    height: number;
}
export interface IBoxSize {
    left: number;
    right: number;
    top: number;
    bottom: number;
}
export interface IContentSize {
    x: number;
    y: number;
    left: number;
    top: number;
    scale: number;
}
